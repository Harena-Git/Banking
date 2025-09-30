using Microsoft.EntityFrameworkCore;
using DepositAccount.Data;
using DepositAccount.Models;

namespace DepositAccount.Services
{
    public class DepotService : IDepotService
    {
        private readonly ApplicationDbContext _context;

        public DepotService(ApplicationDbContext context)
        {
            _context = context;
        }

        public async Task<List<Depot>> GetAllDepotsAsync()
        {
            return await _context.Depots.Include(d => d.Interets).ToListAsync();
        }

        public async Task<Depot?> GetDepotByIdAsync(int id)
        {
            return await _context.Depots.Include(d => d.Interets)
                                       .FirstOrDefaultAsync(d => d.IdDepot == id);
        }

        public async Task<Depot> CreateDepotAsync(Depot depot)
        {
            _context.Depots.Add(depot);
            await _context.SaveChangesAsync();
            return depot;
        }

        public async Task<Depot?> UpdateDepotAsync(int id, Depot depot)
        {
            var existingDepot = await _context.Depots.FindAsync(id);
            if (existingDepot == null) return null;

            existingDepot.DateDepot = depot.DateDepot;
            existingDepot.MontantDepot = depot.MontantDepot;
            existingDepot.TypeDepot = depot.TypeDepot;
            existingDepot.IdHistorique = depot.IdHistorique;
            existingDepot.IdCompteCourant = depot.IdCompteCourant;

            await _context.SaveChangesAsync();
            return existingDepot;
        }

        public async Task<bool> DeleteDepotAsync(int id)
        {
            var depot = await _context.Depots.FindAsync(id);
            if (depot == null) return false;

            _context.Depots.Remove(depot);
            await _context.SaveChangesAsync();
            return true;
        }

        public async Task<List<Depot>> GetDepotsByCompteAsync(int idCompteCourant)
        {
            return await _context.Depots.Where(d => d.IdCompteCourant == idCompteCourant)
                                       .Include(d => d.Interets)
                                       .ToListAsync();
        }
    }
}