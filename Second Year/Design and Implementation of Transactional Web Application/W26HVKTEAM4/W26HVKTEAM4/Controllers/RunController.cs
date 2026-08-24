using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using W26HVKTEAM4.Models;

namespace W26HVKTEAM4.Controllers
{
    public class RunController : Controller
    {
        private readonly H50Hvkv2Team4Context _context;

        public RunController(H50Hvkv2Team4Context context)
        {
            _context = context;
        }

        public async Task<IActionResult> Index()
        {
            return View(await _context.Runs.ToListAsync());
        }

        public async Task<IActionResult> Edit(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var run = await _context.Runs.FindAsync(id);
            var resID = _context.PetReservations.Where(pr => pr.RunId == id).Select(pr => pr.ReservationId);
            ViewBag.ResID = resID;
            if (run == null)
            {
                return NotFound();
            }
            return View(run);
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(int id, [Bind("RunId,Size,Covered,Location,Status")] Run run, int? resID)
        {
            if (id != run.RunId)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(run);
                    await _context.SaveChangesAsync();
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
                if(resID != null)
                {
                    return RedirectToAction("EndPetVisit", "PetReservation", new {id=resID});
                }
                else
                {
                    return RedirectToAction(nameof(Index));
                }
               
            }
            return View(run);
        }
        private bool RunExists(int id)
        {
            return _context.Runs.Any(e => e.RunId == id);
        }
    }
}
