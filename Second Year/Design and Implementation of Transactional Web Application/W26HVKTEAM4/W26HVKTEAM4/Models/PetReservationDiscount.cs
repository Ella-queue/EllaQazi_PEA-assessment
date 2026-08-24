using System;
using System.Collections.Generic;

namespace W26HVKTEAM4.Models;

public partial class PetReservationDiscount
{
    public int DiscountId { get; set; }

    public int PetReservationId { get; set; }

    public string? NullHelper { get; set; }

    public virtual Discount Discount { get; set; } = null!;

    public virtual PetReservation PetReservation { get; set; } = null!;
}
