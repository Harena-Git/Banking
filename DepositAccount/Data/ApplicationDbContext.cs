using Microsoft.EntityFrameworkCore;
using DepositAccount.Models;

namespace DepositAccount.Data
{
    public class ApplicationDbContext : DbContext
    {
        public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options) : base(options)
        {
        }

        public DbSet<Depot> Depots { get; set; }
        public DbSet<Interet> Interets { get; set; }
        public DbSet<HistoriqueGeneraliser> HistoriquesGeneraliser { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);

            // Configure Depot entity
            modelBuilder.Entity<Depot>(entity =>
            {
                entity.HasKey(e => e.IdDepot);
                entity.Property(e => e.MontantDepot).HasPrecision(15, 2);
            });

            // Configure Interet entity
            modelBuilder.Entity<Interet>(entity =>
            {
                entity.HasKey(e => e.IdInteret);
                entity.Property(e => e.PourcentageInteret).HasPrecision(15, 2);
                
                // Relationship with Depot
                entity.HasOne(i => i.Depot)
                      .WithMany(d => d.Interets)
                      .HasForeignKey(i => i.IdDepot);
            });

            // Configure HistoriqueGeneraliser entity
            modelBuilder.Entity<HistoriqueGeneraliser>(entity =>
            {
                entity.HasKey(e => e.IdHistorique);
                entity.Property(e => e.MontantJoueeHistorique).HasPrecision(15, 2);
            });
        }
    }
}