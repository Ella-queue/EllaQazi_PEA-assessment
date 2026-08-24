using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using W26HVKTEAM4.Models;

namespace W26HVKTEAM4.Controllers
{
    public class PetController : Controller
    {
        private readonly H50Hvkv2Team4Context _context;
        public PetController(H50Hvkv2Team4Context context)
        {
            _context = context;
        }

        public async Task<IActionResult> Index(int? id)
        {
            if (!IsSessionValid())
            {
                return RedirectToAction("Login", "Account");
            }

            var sessionUserType = HttpContext.Session.GetString("UserType");
            var sessionUserId = HttpContext.Session.GetInt32("UserId");

            int userId = (int)((sessionUserType?.ToLower() == "employee" && id.HasValue) ? id.Value : sessionUserId.Value);
            ViewData["CurrentlyLoggedInUser"] = userId;

            ViewBag.OwnerId = userId;

            var pets = await _context.Pets
                .Where(p => p.HvkuserId == userId)
                .Include(p => p.Hvkuser)
                .Include(p => p.PetVaccinations)
                .ThenInclude(pv => pv.Vaccination)
                .ToListAsync();
            return View(pets);
        }
        public async Task<IActionResult> Vaccinations(int? id)
        {
            if (!IsSessionValid())
            {
                return RedirectToAction("Login", "Account");
            }

            if (id == null)
            {
                return NotFound();
            }

            var pet = await _context.Pets.FirstOrDefaultAsync(p => p.PetId == id);

            if (pet == null)
            {
                return NotFound();
            }

            ViewBag.PetName = pet.Name;
            ViewBag.OwnerId = pet.HvkuserId;
            ViewData["CurrentlyLoggedInUser"] = pet.HvkuserId;

            var petVaccinations = await _context.PetVaccinations
            .Include(pv => pv.Vaccination)
            .Where(pv => pv.PetId == id)
            .ToListAsync();

            return View(petVaccinations);
        }

        // POST: Pets/Vaccinations
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Vaccinations(int PetId, int[] VaccinationKeys, IFormCollection form)
        {
            if (!IsSessionValid())
            {
                return RedirectToAction("Login", "Account");
            }

            if (VaccinationKeys != null)
            {
                foreach (var vacId in VaccinationKeys)
                {
                    var petVaccination = await _context.PetVaccinations.FindAsync(vacId, PetId);

                    if (petVaccination != null)
                    {
                        string dateString = form[$"ExpiryDates_{vacId}"];

                        if (DateOnly.TryParse(dateString, out DateOnly newExpiryDate))
                        {
                            if (HttpContext.Session.GetString("UserType")?.ToLower() == "customer")
                            {
                                if (petVaccination.ExpiryDate != newExpiryDate)
                                {
                                    petVaccination.ExpiryDate = newExpiryDate;
                                    petVaccination.VaccinationChecked = false;
                                }
                            }
                            else if (HttpContext.Session.GetString("UserType")?.ToLower() == "employee")
                            {
                                petVaccination.ExpiryDate = newExpiryDate;

                                string isVerified = form[$"Verified_{vacId}"];
                                petVaccination.VaccinationChecked = (isVerified == "true");
                            }
                        }
                    }
                }
                await _context.SaveChangesAsync();
            }

            var pet = await _context.Pets.FindAsync(PetId);
            return RedirectToAction(nameof(Index), new { id = pet?.HvkuserId });
        }


        // GET: Pets/Create
        public IActionResult Create(int? id)
        {
            if (!IsSessionValid())
            {
                return RedirectToAction("Login", "Account");
            }

            int userId = id ?? HttpContext.Session.GetInt32("UserId").Value;

            ViewBag.OwnerId = userId;
            ViewData["CurrentlyLoggedInUser"] = userId;

            return View();
        }

        // POST: Pets/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("PetId,Name,Gender,Breed,Birthyear,HvkuserId,DogSize,Climber,Barker,SpecialNotes,Sterilized")] Pet pet)
        {
            if (!IsSessionValid())
            {
                return RedirectToAction("Login", "Account");
            }

            ModelState.Remove("HVKUser");

            if (ModelState.IsValid)
            {
                if (HttpContext.Session.GetString("UserType")?.ToLower() == "customer")
                {
                    pet.HvkuserId = HttpContext.Session.GetInt32("UserId").Value;
                }

                var allVaccines = await _context.Vaccinations.ToListAsync();

                pet.PetVaccinations = new List<PetVaccination>();

                foreach (var vaccine in allVaccines)
                {
                    pet.PetVaccinations.Add(new PetVaccination
                    {
                        VaccinationId = vaccine.VaccinationId,
                        VaccinationChecked = false,
                        ExpiryDate = DateOnly.MinValue
                    });
                }

                _context.Add(pet);
                await _context.SaveChangesAsync();

                return RedirectToAction(nameof(Index), new { id = pet.HvkuserId });
            }

            return View(pet);
        }

        // GET: Pets/Edit/5
        public async Task<IActionResult> Edit(int? id)
        {
            if (!IsSessionValid())
            {
                return RedirectToAction("Login", "Account");
            }

            if (id == null)
            {
                return NotFound();
            }

            var pet = await _context.Pets.FindAsync(id);
            if (pet == null)
            {
                return NotFound();
            }

            ViewData["CurrentlyLoggedInUser"] = pet.HvkuserId;
            return View(pet);
        }

        // POST: Pets/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(int id, [Bind("PetId,Name,Gender,Breed,Birthyear,HvkuserId,DogSize,Climber,Barker,SpecialNotes,Sterilized")] Pet pet)
        {
            if (!IsSessionValid())
            {
                return RedirectToAction("Login", "Account");
            }

            if (id != pet.PetId)
            {
                return NotFound();
            }

            ModelState.Remove("HVKUser");

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(pet);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!PetExists(pet.PetId))
                    {
                        return NotFound();
                    }
                    else
                    {
                        throw;
                    }
                }
                return RedirectToAction(nameof(Index), new { id = pet.HvkuserId });
            }
            ViewData["CurrentlyLoggedInUser"] = pet.HvkuserId;
            return View(pet);
        }

        // GET: Pets/Delete/5
        public async Task<IActionResult> Delete(int? id)
        {
            if (!IsSessionValid())
            {
                return RedirectToAction("Login", "Account");
            }

            if (id == null)
            {
                return NotFound();
            }

            var pet = await _context.Pets
                .Include(p => p.Hvkuser)
                .FirstOrDefaultAsync(m => m.PetId == id);
            if (pet == null)
            {
                return NotFound();
            }
            ViewData["CurrentlyLoggedInUser"] = pet.HvkuserId;
            return View(pet);
        }

        // POST: Pets/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(int id)
        {
            if (!IsSessionValid())
            {
                return RedirectToAction("Login", "Account");
            }

            var pet = await _context.Pets.FindAsync(id);

            if (pet == null)
            {
                return NotFound();
            }

            int ownerId = pet.HvkuserId;

            var associatedVaccinations = await _context.PetVaccinations
                .Where(pv => pv.PetId == id)
                .ToListAsync();

            if (associatedVaccinations.Any())
            {
                _context.PetVaccinations.RemoveRange(associatedVaccinations);
            }

            _context.Pets.Remove(pet);

            try
            {
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index), new { id = ownerId });
            }
            catch (DbUpdateException)
            {

                ViewBag.ErrorMessage = "This pet cannot be deleted because it is linked to existing reservations. Please cancel or remove the reservations first.";
                ViewData["CurrentlyLoggedInUser"] = ownerId;
                ViewBag.OwnerId = ownerId;

                return View(pet);
            }
        }

        private bool PetExists(int id)
        {
            return _context.Pets.Any(e => e.PetId == id);
        }

        private bool IsSessionValid()
        {
            bool hasUserId = HttpContext.Session.GetInt32("UserId").HasValue;
            bool isNotLoggedOut = HttpContext.Session.GetString("Login") != "No";

            return hasUserId && isNotLoggedOut;
        }
    }
}
