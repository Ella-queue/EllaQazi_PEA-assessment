using System;
using System.Collections.Generic;

namespace W26HVKTEAM4.Models;

public partial class Discount
{
    public int DiscountId { get; set; }

    public string Desciption { get; set; } = null!;

    public decimal Percentage { get; set; }

    public string Type { get; set; } = null!;

    public virtual ICollection<PetReservationDiscount> PetReservationDiscounts { get; set; } = new List<PetReservationDiscount>();

    public virtual ICollection<ReservationDiscount> ReservationDiscounts { get; set; } = new List<ReservationDiscount>();
}
