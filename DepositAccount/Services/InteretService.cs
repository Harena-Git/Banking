using Microsoft.EntityFrameworkCore;
using DepositAccount.Data;
using DepositAccount.Models;

namespace DepositAccount.Services
{
    public class InteretService : IInteretService
    {
        private readonly ApplicationDbContext _context;

        public InteretService(ApplicationDbContext context)
        {
            _context = context;
        }

        public async Task<List<Interet>> GetAllInteretsAsync()
        {
            return await _context.Interets.Include(i => i.Depot).ToListAsync();
        }

        public async Task<Interet?> GetInteretByIdAsync(int id)
        {
            return await _context.Interets.Include(i => i.Depot)
                                         .FirstOrDefaultAsync(i => i.IdInteret == id);
        }

        public async Task<Interet> CreateInteretAsync(Interet interet)
        {
            _context.Interets.Add(interet);
            await _context.SaveChangesAsync();
            return interet;
        }

        public async Task<Interet?> UpdateInteretAsync(int id, Interet interet)
        {
            var existingInteret = await _context.Interets.FindAsync(id);
            if (existingInteret == null) return null;

            existingInteret.PourcentageInteret = interet.PourcentageInteret;
            existingInteret.DurreeInteret = interet.DurreeInteret;
            existingInteret.IdDepot = interet.IdDepot;

            await _context.SaveChangesAsync();
            return existingInteret;
        }

        public async Task<bool> DeleteInteretAsync(int id)
        {
            var interet = await _context.Interets.FindAsync(id);
            if (interet == null) return false;

            _context.Interets.Remove(interet);
            await _context.SaveChangesAsync();
            return true;
        }

        public async Task<List<Interet>> GetInteretsByDepotAsync(int idDepot)
        {
            return await _context.Interets.Where(i => i.IdDepot == idDepot)
                                         .Include(i => i.Depot)
                                         .ToListAsync();
        }

        public async Task<decimal> CalculateInteretAsync(decimal montant, decimal pourcentage, int duree)
        {
            // Calcul d'intérêt simple : montant * taux * durée
            return montant * (pourcentage / 100) * duree;
        }
    }
}