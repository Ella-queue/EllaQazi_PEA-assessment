using System;
using System.Collections.Generic;

namespace W26HVKTEAM4.Models;

public partial class Run
{
    public int RunId { get; set; }

    public string Size { get; set; } = null!;

    public bool Covered { get; set; }

    public string? Location { get; set; }

    public decimal? Status { get; set; }

    public virtual ICollection<PetReservation> PetReservations { get; set; } = new List<PetReservation>();
}
