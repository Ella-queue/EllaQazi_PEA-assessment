using System;
using System.Collections.Generic;

namespace W26HVKTEAM4.Models;

public partial class PetVaccination
{
    public DateOnly ExpiryDate { get; set; }

    public int VaccinationId { get; set; }

    public int PetId { get; set; }

    public bool VaccinationChecked { get; set; }

    public virtual Pet Pet { get; set; } = null!;

    public virtual Vaccination Vaccination { get; set; } = null!;
}
