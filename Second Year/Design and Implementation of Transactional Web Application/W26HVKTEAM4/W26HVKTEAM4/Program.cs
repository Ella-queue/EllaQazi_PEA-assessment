using Microsoft.EntityFrameworkCore;

using W26HVKTEAM4.Models;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddDistributedMemoryCache();
builder.Services.AddSession(options =>
{
    options.IdleTimeout = TimeSpan.FromMinutes(10);
    options.Cookie.Name = "UserSession";
});
//builder.Services.AddTransient<CustomerFormattingService>();
builder.Services.AddControllersWithViews();

builder.Services.AddDbContext<H50Hvkv2Team4Context>(options =>

options.UseSqlServer(builder.Configuration.GetConnectionString("MyConnection"))

);

var app = builder.Build();

// Configure the HTTP request pipeline.
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Home/Error");
}
app.UseStaticFiles();

app.UseRouting();

app.UseAuthorization();

app.UseSession();

app.MapControllerRoute(
    name: "default",
    pattern: "{controller=Account}/{action=Login}/{id?}");

app.Run();
