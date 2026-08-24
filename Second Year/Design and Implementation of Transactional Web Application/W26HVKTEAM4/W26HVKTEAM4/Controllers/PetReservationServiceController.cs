using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using W26HVKTEAM4.Models;

namespace W26HVKTEAM4.Controllers
{
    public class PetReservationServiceController : Controller
    {
        private readonly H50Hvkv2Team4Context _db;

        public PetReservationServiceController(H50Hvkv2Team4Context db)
        {
            _db = db;
        }

        [HttpGet]
        public IActionResult Create(int petReservationId, int reservationId)
        {
            var petReservation = _db.PetReservations
                .Include(pr => pr.Pet)
                .Include(pr => pr.PetReservationServices)
                .FirstOrDefault(pr => pr.PetReservationId == petReservationId);

            if (petReservation == null)
            {
                return NotFound();
            }

            var existingServiceIds = petReservation.PetReservationServices
                .Select(prs => prs.ServiceId)
                .ToList();

            var services = _db.Services
                .Where(s => !existingServiceIds.Contains(s.ServiceId))
                .OrderBy(s => s.ServiceDescription)
                .ToList();

            ViewData["Services"] = services;
            ViewData["PetName"] = petReservation.Pet.Name;
            ViewData["ReservationId"] = reservationId; 

            PetReservationService petReservationService = new PetReservationService
            {
                PetReservationId = petReservationId
            };

            return View(petReservationService);
        }

        [HttpPost]
        public IActionResult Create(PetReservationService petReservationService, int reservationId, string submitButton)
        {
            var petReservation = _db.PetReservations
                .Include(pr => pr.Pet)
                .Include(pr => pr.Reservation)
                .FirstOrDefault(pr => pr.PetReservationId == petReservationService.PetReservationId);

            if (petReservation == null)
            {
                return NotFound();
            }

            var existingServiceIds = _db.PetReservationServices
                .Where(prs => prs.PetReservationId == petReservationService.PetReservationId)
                .Select(prs => prs.ServiceId)
                .ToList();

            var services = _db.Services
                .Where(s => !existingServiceIds.Contains(s.ServiceId))
                .OrderBy(s => s.ServiceDescription)
                .ToList();

            ViewData["Services"] = services;
            ViewData["PetName"] = petReservation.Pet.Name;

            bool alreadyExists = _db.PetReservationServices.Any(prs =>
                prs.PetReservationId == petReservationService.PetReservationId &&
                prs.ServiceId == petReservationService.ServiceId);

            if (alreadyExists)
            {
                ModelState.AddModelError("", "That service has already been added to this pet.");
            }

            if (!ModelState.IsValid)
            {
                return View(petReservationService);
            }

            _db.PetReservationServices.Add(petReservationService);
            _db.SaveChanges();

            if (submitButton == "Add Another Service")
            {
                return RedirectToAction("Create", new
                {
                    petReservationId = petReservationService.PetReservationId,
                    reservationId = reservationId
                });
            }

            return RedirectToAction("ReservationServices", new
            {
                reservationId = reservationId
            });
        }

        [HttpGet]
        public IActionResult Delete(int petReservationId, int serviceId)
        {
            var petReservationService = _db.PetReservationServices
                .Include(prs => prs.Service)
                .Include(prs => prs.PetReservation)
                    .ThenInclude(pr => pr.Pet)
                .Include(prs => prs.PetReservation)
                    .ThenInclude(pr => pr.Reservation)
                .FirstOrDefault(prs => prs.PetReservationId == petReservationId && prs.ServiceId == serviceId);

            if (petReservationService == null)
            {
                return NotFound();
            }

            return View(petReservationService);
        }

        [HttpPost, ActionName("Delete")]
        public IActionResult DeleteConfirmed(int petReservationId, int serviceId)
        {
            var petReservationService = _db.PetReservationServices
                .Include(prs => prs.PetReservation)
                .FirstOrDefault(prs => prs.PetReservationId == petReservationId && prs.ServiceId == serviceId);

            if (petReservationService == null)
            {
                return NotFound();
            }

            int reservationId = petReservationService.PetReservation.ReservationId;

            _db.PetReservationServices.Remove(petReservationService);
            _db.SaveChanges();

            return RedirectToAction("Edit", "Reservation", new { id = reservationId });
        }
        public IActionResult ReservationServices(int reservationId)
        {
            var reservation = _db.Reservations
                .Include(r => r.PetReservations)
                    .ThenInclude(pr => pr.Pet)
                .Include(r => r.PetReservations)
                    .ThenInclude(pr => pr.PetReservationServices)
                        .ThenInclude(prs => prs.Service)
                .FirstOrDefault(r => r.ReservationId == reservationId);

            if (reservation == null)
            {
                return NotFound();
            }

            return View(reservation);
        }
    }
}