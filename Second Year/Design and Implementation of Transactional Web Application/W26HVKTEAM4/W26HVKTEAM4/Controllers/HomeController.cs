using System.Diagnostics;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using W26HVKTEAM4.Models;

namespace W26HVKTEAM4.Controllers
{
    public class HomeController : Controller
    {
        private readonly ILogger<HomeController> _logger;
        private readonly H50Hvkv2Team4Context _db;

        public HomeController(ILogger<HomeController> logger, H50Hvkv2Team4Context db)
        {
            _logger = logger;
            _db = db;
        }

        public IActionResult Index()
        {
            HttpContext.Session.SetString("Login", "No");
            ViewBag.Login = HttpContext.Session.GetString("Login");
            return View();
            //return RedirectToAction("Login", "Account");
        }

        public async Task<IActionResult> Employee()
        {
            ViewBag.Login = HttpContext.Session.GetString("Login");
            if (HttpContext.Session.GetString("Login") == "No")
            {
                return RedirectToAction("Index", "Home");
            }
            ViewBag.User = HttpContext.Session.GetString("UserType");

            ViewBag.UserName = _db.Hvkusers
               .Where(u => u.HvkuserId == HttpContext.Session.GetInt32("UserId"))
               .Select(u => u.FirstName + " " + u.LastName)
               .FirstOrDefault();

            var reservations = _db.Reservations
                .Include(r => r.PetReservations)
                .ThenInclude(pr => pr.Pet)
                .ThenInclude(p => p.Hvkuser);

            return View(await reservations.ToListAsync());
        }
        public async Task<IActionResult> Customer(int? customerID)
        {
            ViewBag.Login = HttpContext.Session.GetString("Login");
            if (HttpContext.Session.GetString("Login") == "No")
            {
                return RedirectToAction("Index", "Home");
            }
            if (customerID == null)
            {
                customerID = HttpContext.Session.GetInt32("UserId").Value;
            }
            if (customerID == null || customerID == 0)
            {
                return RedirectToAction("Index", "Home");
            }

            var user = await _db.Hvkusers.FirstOrDefaultAsync(u => u.HvkuserId == customerID);

            if (user == null)
            {
                return RedirectToAction("Index", "Home");
            }

            ViewBag.CustomerInfo = user;

            var reservations = _db.Reservations
                .Include(r => r.PetReservations)
                .ThenInclude(pr => pr.Pet)
                .ThenInclude(p => p.Hvkuser)
                .Where(r => r.PetReservations.First().Pet.HvkuserId == customerID);

            return View(await reservations.ToListAsync());
        }

        public IActionResult UnderConstruction(decimal? customerId)
        {
            ViewBag.Login = HttpContext.Session.GetString("Login");
            ViewBag.User = HttpContext.Session.GetString("UserType");
            if (customerId == null)
            {
                ViewData["CurrentlyLoggedInUser"] = HttpContext.Session.GetInt32("UserId").Value;
            }
            else
            {
                ViewData["CurrentlyLoggedInUser"] = customerId;
            }
            if (HttpContext.Session.GetString("Login") == "No")
            {
                return RedirectToAction("Index", "Home");
            }
            return View();
        }


        public IActionResult Privacy()
        {
            ViewBag.Login = HttpContext.Session.GetString("Login");
            if (HttpContext.Session.GetString("Login") == "No")
            {
                return RedirectToAction("Index", "Home");
            }
            return View();
        }

        [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
        public IActionResult Error()
        {
            return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
        }
    }
}
