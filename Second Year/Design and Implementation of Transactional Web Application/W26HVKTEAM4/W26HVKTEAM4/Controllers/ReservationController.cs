using System.Diagnostics;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using W26HVKTEAM4.Models;

namespace W26HVKTEAM4.Controllers
{
    public class ReservationController : Controller
    {
        private readonly H50Hvkv2Team4Context _db;

        public ReservationController(H50Hvkv2Team4Context db)
        {
            _db = db;
        }

        public IActionResult Index()
        {
            int? userId = HttpContext.Session.GetInt32("UserId");
            string? userType = HttpContext.Session.GetString("UserType");

            var query = _db.Reservations
                .Include(r => r.PetReservations)
                    .ThenInclude(pr => pr.Pet)
                        .ThenInclude(p => p.Hvkuser)
                .Include(r => r.PetReservations)
                    .ThenInclude(pr => pr.PetReservationServices)
                        .ThenInclude(prs => prs.Service)
                .AsQueryable();

            if (userType == "Customer" && userId.HasValue)
            {
                query = query.Where(r => r.PetReservations.Any(pr => pr.Pet.HvkuserId == userId.Value));
            }
            else
            {
                query = query.Where(r => r.StartDate.CompareTo(DateOnly.FromDateTime(DateTime.Now)) >= 0);
            }

            var today = DateOnly.FromDateTime(DateTime.Today);
            query = query
                .OrderBy(r => r.EndDate < today ? 2 : r.StartDate > today ? 1 : 0)
                .ThenBy(r => r.StartDate);

            var reservations = query.ToList();
            return View(reservations);
        }

        [HttpGet]
        public IActionResult Create()
        {
            if (HttpContext.Session.GetString("UserType") == "Employee")
            {
                ViewData["Customers"] = _db.Hvkusers
                    .Where(u => u.UserType == "Customer")
                    .OrderBy(u => u.LastName)
                    .ThenBy(u => u.FirstName)
                    .ToList();
            }
            return View();
        }

        [HttpPost]
        public async Task<IActionResult> Create(Reservation reservation, int? customerId)
        {
            if (HttpContext.Session.GetString("UserType") == "Employee" && (!customerId.HasValue || customerId.Value == 0))
            {
                ModelState.AddModelError("", "Please select a customer.");
                ViewData["Customers"] = _db.Hvkusers
                    .Where(u => u.UserType == "Customer")
                    .OrderBy(u => u.LastName)
                    .ThenBy(u => u.FirstName)
                    .ToList();
                return View(reservation);
            }

            if (!ModelState.IsValid)
            {
                if (HttpContext.Session.GetString("UserType") == "Employee")
                {
                    ViewData["Customers"] = _db.Hvkusers
                        .Where(u => u.UserType == "Customer")
                        .OrderBy(u => u.LastName)
                        .ThenBy(u => u.FirstName)
                        .ToList();
                }
                return View(reservation);
            }

            _db.Reservations.Add(reservation);
            await _db.SaveChangesAsync();

            if (HttpContext.Session.GetString("UserType") == "Employee")
            {
                return RedirectToAction("Create", "PetReservation", new { reservationId = reservation.ReservationId, customerId = customerId.Value });
            }

            return RedirectToAction("Create", "PetReservation", new { reservationId = reservation.ReservationId });
        }

        [HttpGet]
        public IActionResult Edit(int id)
        {
            var reservation = _db.Reservations
                .Include(r => r.PetReservations).ThenInclude(pr => pr.Pet)
                .Include(r => r.PetReservations).ThenInclude(pr => pr.PetReservationServices).ThenInclude(prs => prs.Service)
                .FirstOrDefault(r => r.ReservationId == id);

            if (reservation == null) return NotFound();
            return View(reservation);
        }

        [HttpPost]
        public IActionResult Edit(Reservation reservation)
        {
            if (!ModelState.IsValid)
            {
                var reservationWithPets = _db.Reservations
                    .Include(r => r.PetReservations).ThenInclude(pr => pr.Pet)
                    .FirstOrDefault(r => r.ReservationId == reservation.ReservationId);
                return View(reservationWithPets);
            }

            var existingReservation = _db.Reservations.FirstOrDefault(r => r.ReservationId == reservation.ReservationId);
            if (existingReservation == null) return NotFound();

            existingReservation.StartDate = reservation.StartDate;
            existingReservation.EndDate = reservation.EndDate;
            _db.SaveChanges();

            return RedirectToAction("Success", new { message = "Reservation was successfully updated." });
        }

        [HttpGet]
        public IActionResult Delete(int id)
        {
            var reservation = _db.Reservations
                .Include(r => r.PetReservations).ThenInclude(pr => pr.Pet)
                .FirstOrDefault(r => r.ReservationId == id);

            if (reservation == null) return NotFound();
            return View(reservation);
        }

        [HttpPost, ActionName("Delete")]
        public IActionResult DeleteConfirmed(int id)
        {
            var reservation = _db.Reservations
                .Include(r => r.PetReservations).ThenInclude(pr => pr.PetReservationServices)
                .Include(r => r.PetReservations).ThenInclude(pr => pr.Medications)
                .Include(r => r.PetReservations).ThenInclude(pr => pr.PetReservationDiscounts)
                .FirstOrDefault(r => r.ReservationId == id);

            if (reservation == null) return NotFound();

            foreach (var petReservation in reservation.PetReservations.ToList())
            {
                if (petReservation.PetReservationServices.Any()) _db.PetReservationServices.RemoveRange(petReservation.PetReservationServices);
                if (petReservation.Medications.Any()) _db.Medications.RemoveRange(petReservation.Medications);
                if (petReservation.PetReservationDiscounts.Any()) _db.PetReservationDiscounts.RemoveRange(petReservation.PetReservationDiscounts);
            }

            if (reservation.PetReservations.Any()) _db.PetReservations.RemoveRange(reservation.PetReservations);
            _db.Reservations.Remove(reservation);
            _db.SaveChanges();

            return RedirectToAction("Success", new { message = "Reservation was successfully deleted." });
        }

        [HttpGet]
        public IActionResult Success(string message)
        {
            ViewBag.Message = message;
            return View();
        }

        [HttpGet]
        public async Task<IActionResult> StartVisit(int? id)
        {
            if (id == null) return NotFound();

            var reservation = await _db.Reservations
                .Include(r => r.PetReservations).ThenInclude(pr => pr.Pet).ThenInclude(p => p.PetVaccinations).ThenInclude(pv => pv.Vaccination)
                .Include(r => r.PetReservations).ThenInclude(pr => pr.PetReservationServices).ThenInclude(prs => prs.Service).ThenInclude(s => s.DailyRates)
                .FirstOrDefaultAsync(m => m.ReservationId == id);

            if (reservation == null) return NotFound();

            ViewBag.AvailableRuns = await _db.Runs.Where(r => r.Status == 1).ToListAsync();
            return View(reservation);
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> StartVisit(int ReservationId, string[] VaccinationKeys, IFormCollection form)
        {
            var reservation = await _db.Reservations
                .Include(r => r.PetReservations)
                .FirstOrDefaultAsync(r => r.ReservationId == ReservationId);

            if (reservation == null) return NotFound();

            if (VaccinationKeys != null)
            {
                foreach (var key in VaccinationKeys)
                {
                    var idParts = key.Split('|');
                    if (idParts.Length != 2 || !int.TryParse(idParts[0], out int petId) || !int.TryParse(idParts[1], out int vacId))
                        continue;

                    var petVaccination = await _db.PetVaccinations.FindAsync(petId, vacId);
                    if (petVaccination == null) continue;

                    if (DateOnly.TryParse(form[$"ExpiryDates_{petId}_{vacId}"], out DateOnly newExpiryDate))
                    {
                        petVaccination.ExpiryDate = newExpiryDate;
                    }

                    string isVerified = form[$"Verified_{petId}_{vacId}"];
                    petVaccination.VaccinationChecked = (isVerified == "true");
                }
            }

            foreach (var pr in reservation.PetReservations)
            {
                if (int.TryParse(form[$"RunIds_{pr.PetReservationId}"], out int runId))
                {
                    pr.RunId = runId;
                }
            }

            reservation.Status = 3;

            try
            {
                await _db.SaveChangesAsync();
                return RedirectToAction("Employee", "Home");
            }
            catch (Exception)
            {
                return RedirectToAction("Error", "Home");
            }
        }

        [HttpGet]
        public async Task<IActionResult> Invoice(int reservationId)
        {
            var reservation = await _db.Reservations
                .Include(r => r.PetReservations)
                .ThenInclude(pr => pr.Pet)
                .Include(r => r.PetReservations)
                .ThenInclude(pr => pr.PetReservationServices)
                .ThenInclude(prs => prs.Service)
                .ThenInclude(s => s.DailyRates)
                .Include(r => r.PetReservations)
                .ThenInclude(pr => pr.PetReservationDiscounts)
                .ThenInclude(prd => prd.Discount)
                .Include(r => r.ReservationDiscounts)
                .ThenInclude(rd => rd.Discount)
                .FirstOrDefaultAsync(r => r.ReservationId == reservationId);

            if (reservation == null)
            {
                return NotFound();
            }

            return View(reservation);
        }

        [HttpGet]
        public async Task<IActionResult> EndReservationComplete(int reservationId)
        {
            var reservation = await _db.Reservations
                .Include(r => r.PetReservations).ThenInclude(pr => pr.Pet).ThenInclude(p => p.Hvkuser)
                .Include(r => r.PetReservations).ThenInclude(pr => pr.PetReservationServices).ThenInclude(prs => prs.Service).ThenInclude(dr => dr.DailyRates)
                .Include(r => r.PetReservations).ThenInclude(prd => prd.PetReservationDiscounts).ThenInclude(d => d.Discount)
                .Include(rd => rd.ReservationDiscounts).ThenInclude(d => d.Discount)
                .FirstOrDefaultAsync(r => r.ReservationId == reservationId);

            if (reservation == null)
            {
                return RedirectToAction("Index");
            }

            reservation.Status = 5;

            try
            {
                await _db.SaveChangesAsync();
                return View(reservation);
            }
            catch (Exception)
            {
                return RedirectToAction("Error", "Home");
            }
        }
    }
}