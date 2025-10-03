using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace DepositAccount.Models
{
    public class Interet
    {
        [Key]
        public int IdInteret { get; set; }
        
        [Column(TypeName = "decimal(15,2)")]
        public decimal PourcentageInteret { get; set; }
        
        public int DurreeInteret { get; set; }
        
        [Required]
        public int IdDepot { get; set; }
        
        // Navigation property
        public virtual Depot? Depot { get; set; }
    }
}