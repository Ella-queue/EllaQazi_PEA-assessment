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
    public class CustomerController : Controller
    {
        private readonly H50Hvkv2Team4Context _context;

        public CustomerController(H50Hvkv2Team4Context context)
        {
            _context = context;
        }

        // GET: Customer
        public async Task<IActionResult> Index(string? search)
        {
            
            if(search == null || search.Trim() == ""  )
            {
                return View(await _context.Hvkusers.ToListAsync());
            }
            else
            {
                var searchUsers = await _context.Hvkusers.Where(
                    s => ((s.FirstName + s.LastName).Trim().ToLower().Contains(search.Trim().ToLower().Replace(" ", "")))
                    ||(s.Phone.Contains(search.Trim().ToLower().Replace(" ", ""))) 
                    || (s.CellPhone.Contains(search.Trim().ToLower().Replace(" ", ""))) 
                    || (s.Email.Contains(search.Trim().ToLower().Replace(" ", "")))
                )
                .ToListAsync();

                return View(searchUsers);
            }
        }

        // GET: Customer/Details/5
        public async Task<IActionResult> Details(int? id)
        {
            ViewBag.Login = HttpContext.Session.GetString("Login");
            if (HttpContext.Session.GetString("Login") == "No")
            {
                return RedirectToAction("Index", "Home");
            }
            if (id == null)
            {
                return NotFound();
            }

            var hvkuser = await _context.Hvkusers.Include(p => p.Pets).Where(c => c.HvkuserId == id).FirstOrDefaultAsync();
            if (hvkuser == null)
            {
                return NotFound();
            }
            
                return View(hvkuser);
        }

        //HttpGet Update Action
        public async Task<IActionResult> Update(int? id) {

            var user = HttpContext.Session.GetInt32("UserId");
            
            if (id == null) {
                id = user;

                var customer = await _context.Hvkusers.FindAsync(id);

                if (customer == null)
                {
                    return NotFound();
                }

                return View(customer);
            }
            else
            {
                var customer = await _context.Hvkusers.FindAsync(id);

                if (customer == null)
                {
                    return NotFound();
                }

                return View(customer);
            }
        }

        //HttpPost Update Action
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Update(int? id, Hvkuser user) {
            

            if (id != user.HvkuserId) {
                return NotFound();
            }
            if (ModelState.IsValid) {
                try
                {
                    _context.Update(user);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException) {
                    if (!UserExists((int)id))
                    {
                        return NotFound();
                    }
                    else
                    {
                        throw;
                    }
                }
                
               return RedirectToAction("Details", new { id = id });
                
            }
            return View(user);
        }
        //HttpGet Create
        public IActionResult Create()
        {

            ViewBag.User = HttpContext.Session.GetString("UserType");

            return View();
            
        }
        //HttpPost Create
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create(Hvkuser user)
        {
            
            if (ModelState.IsValid)
            {
                _context.Add(user);
                await _context.SaveChangesAsync();

                return RedirectToAction("Details", new { id = user.HvkuserId});
            }
            else
            {
                return View(user);
            }
                
        }
        //HttpGet Delete
        public async Task<IActionResult> Delete(int? id) {

            if (id == null)
            {
                return NotFound();
            }
            else
            {
                var user = await _context.Hvkusers.FindAsync(id);
                if (user == null) { 
                    return NotFound();
                }

                return View(user);
            }
                
        }
        //HttpPost Delete
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(int? id)
        {
            var usertype = HttpContext.Session.GetString("UserType") ;

            

            var user = await _context.Hvkusers.FindAsync(id);
            if (user != null) {
                _context.Hvkusers.Remove(user);
            }
            try { 
                await _context.SaveChangesAsync();
                if(usertype == "Customer")
                {
                    HttpContext.Session.Remove("UserId");
                    HttpContext.Session.Remove("userType");
                    return RedirectToAction("Login", "Account");
                }
                return RedirectToAction("index");
            }
            catch (DbUpdateException) {
                ViewBag.ErrorMessage = "This user has active reservations and pets, cannot delete user";
                return View();
            }
        }
        // UserExists
        private bool UserExists(int id)
        {
            return _context.Hvkusers.Any(u => u.HvkuserId == id);
        }

    }
}
