using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using W26HVKTEAM4.Models;

namespace W26HVKTEAM4.Controllers
{
    public class PetReservationController : Controller
    {
        private readonly H50Hvkv2Team4Context _db;

        public PetReservationController(H50Hvkv2Team4Context db)
        {
            _db = db;
        }
        private List<Pet> GetPetsForReservation(int reservationId, int? customerId = null)
        {
            int userId = HttpContext.Session.GetInt32("UserId").Value;
            string userType = HttpContext.Session.GetString("UserType");

            int petOwnerId = userId;

            if (userType == "Employee")
            {
                if (customerId.HasValue && customerId.Value != 0)
                {
                    petOwnerId = customerId.Value;
                }
                else
                {
                    var reservation = _db.Reservations
                        .Include(r => r.PetReservations)
                            .ThenInclude(pr => pr.Pet)
                        .FirstOrDefault(r => r.ReservationId == reservationId);

                    if (reservation != null && reservation.PetReservations.Any())
                    {
                        petOwnerId = reservation.PetReservations.First().Pet.HvkuserId;
                    }
                }
            }

            return _db.Pets
                .Where(p => p.HvkuserId == petOwnerId)
                .ToList();
        }
        public async Task<IActionResult> EndPetVisit(int id)
        {
            var petReservations = _db.Reservations.Where(r => r.ReservationId == id).Include(r => r.PetReservations).ThenInclude(pr => pr.PetReservationServices).ThenInclude(ps => ps.Service).ThenInclude(s => s.DailyRates).Include(r => r.PetReservations).ThenInclude(pr => pr.Pet).Include(r => r.PetReservations).ThenInclude(pr => pr.Run);
            return View(await petReservations.ToListAsync());
        }

        public async Task<IActionResult> Edit(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var run = await _db.Runs.FindAsync(id);
            if (run == null)
            {
                return NotFound();
            }
            return View(run);
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(int id, [Bind("RunId,Size,Covered,Location,Status")] Run run)
        {
            if (id != run.RunId)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _db.Update(run);
                    await _db.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!RunExists(run.RunId))
                    {
                        return NotFound();
                    }
                    else
                    {
                        throw;
                    }
                }
                return RedirectToAction(nameof(Index));
            }
            return View(run);
        }
        private bool RunExists(int id)
        {
            return _db.Runs.Any(e => e.RunId == id);
        }

        [HttpGet]
        public IActionResult Create(int reservationId, int? customerId)
        {
            ViewData["Pets"] = GetPetsForReservation(reservationId, customerId);

            PetReservation petReservation = new PetReservation
            {
                ReservationId = reservationId
            };

            ViewData["CustomerId"] = customerId;

            return View(petReservation);
        }

        [HttpPost]
        public IActionResult Create(PetReservation petReservation, string submitButton, int? customerId)
        {
            ViewData["Pets"] = GetPetsForReservation(petReservation.ReservationId, customerId);
            ViewData["CustomerId"] = customerId;

            bool alreadyExists = _db.PetReservations.Any(pr =>
                pr.ReservationId == petReservation.ReservationId &&
                pr.PetId == petReservation.PetId);

            if (alreadyExists)
            {
                ModelState.AddModelError("", "That pet has already been added to this reservation.");
            }

            if (!ModelState.IsValid)
            {
                return View(petReservation);
            }

            _db.PetReservations.Add(petReservation);
            _db.SaveChanges();

            if (submitButton == "Add Another Pet")
            {
                return RedirectToAction("Create", "PetReservation", new
                {
                    reservationId = petReservation.ReservationId,
                    customerId = customerId
                });
            }

            return RedirectToAction("ReservationServices", "PetReservationService",
                new { reservationId = petReservation.ReservationId });
        }
        [HttpGet]
        public IActionResult Delete(int id)
        {
            var petReservation = _db.PetReservations
                .Include(pr => pr.Pet)
                .Include(pr => pr.Reservation)
                .FirstOrDefault(pr => pr.PetReservationId == id);

            if (petReservation == null)
            {
                return NotFound();
            }

            return View(petReservation);
        }

        [HttpPost, ActionName("Delete")]
        public IActionResult DeleteConfirmed(int id)
        {
            var petReservation = _db.PetReservations
                .Include(pr => pr.PetReservationServices)
                .Include(pr => pr.Medications)
                .Include(pr => pr.PetReservationDiscounts)
                .FirstOrDefault(pr => pr.PetReservationId == id);

            if (petReservation == null)
            {
                return NotFound();
            }

            int reservationId = petReservation.ReservationId;

            if (petReservation.PetReservationServices.Any())
            {
                _db.PetReservationServices.RemoveRange(petReservation.PetReservationServices);
            }

            if (petReservation.Medications.Any())
            {
                _db.Medications.RemoveRange(petReservation.Medications);
            }

            if (petReservation.PetReservationDiscounts.Any())
            {
                _db.PetReservationDiscounts.RemoveRange(petReservation.PetReservationDiscounts);
            }

            _db.PetReservations.Remove(petReservation);
            _db.SaveChanges();

            return RedirectToAction("Edit", "Reservation", new { id = reservationId });
        }
    }
}