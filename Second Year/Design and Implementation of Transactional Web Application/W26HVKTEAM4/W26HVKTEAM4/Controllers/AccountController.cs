using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using W26HVKTEAM4.Models;

namespace W26HVKTEAM4.Controllers
{
    public class AccountController : Controller
    {
        private readonly H50Hvkv2Team4Context _context;

        public AccountController(H50Hvkv2Team4Context context)
        {
            _context = context;
        }

        [HttpGet]
        public IActionResult Login()
        {
            HttpContext.Session.SetString("Login", "No");
            ViewBag.Login = HttpContext.Session.GetString("Login");
            return View();
        }

        [HttpPost]
        public IActionResult Login(Hvkuser userLogin)
        {
            HttpContext.Session.SetString("Login", "No");
            var user = _context.Hvkusers.FirstOrDefault(u =>
                u.Email == userLogin.Email &&
                u.Password == userLogin.Password);

            if (user == null)
            {
                ViewBag.Error = "Invalid email or password credentials.";
                return View(userLogin);
            }

            HttpContext.Session.SetInt32("UserId", user.HvkuserId);
            HttpContext.Session.SetString("UserType", user.UserType);
            HttpContext.Session.SetString("UserEmail", user.Email);
            TempData["UserName"] = $"{user.FirstName} {user.LastName}";

            if (HttpContext.Session.GetString("UserType").ToLower() == "employee")
            {
                HttpContext.Session.SetString("Login", "Yes");
                ViewBag.Login = HttpContext.Session.GetString("Login");
                return RedirectToAction("Employee", "Home");
            }

            if (HttpContext.Session.GetString("UserType").ToLower() == "customer")
            {
                HttpContext.Session.SetString("Login", "Yes");
                ViewBag.Login = HttpContext.Session.GetString("Login");
                return RedirectToAction("Customer", "Home", new { customerId=user.HvkuserId});
            }

            HttpContext.Session.SetString("Login", "No");
            ViewBag.Error = "User type not recognized.";
            return View(userLogin);
        }
        public IActionResult Logout()
        {
            ViewBag.Login = HttpContext.Session.GetString("Login");
            HttpContext.Session.Remove("UserId");
            HttpContext.Session.Remove("UserType");
            HttpContext.Session.Remove("UserEmail");
            HttpContext.Session.SetString("Login", "No");
            return RedirectToAction("Login", "Account");
        }
    }
}