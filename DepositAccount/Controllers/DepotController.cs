using Microsoft.AspNetCore.Mvc;
using DepositAccount.Models;
using DepositAccount.Services;

namespace DepositAccount.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class DepotController : ControllerBase
    {
        private readonly IDepotService _depotService;

        public DepotController(IDepotService depotService)
        {
            _depotService = depotService;
        }

        [HttpGet]
        public async Task<ActionResult<List<Depot>>> GetAllDepots()
        {
            var depots = await _depotService.GetAllDepotsAsync();
            return Ok(depots);
        }

        [HttpGet("{id}")]
        public async Task<ActionResult<Depot>> GetDepotById(int id)
        {
            var depot = await _depotService.GetDepotByIdAsync(id);
            if (depot == null) return NotFound();
            return Ok(depot);
        }

        [HttpGet("compte/{idCompteCourant}")]
        public async Task<ActionResult<List<Depot>>> GetDepotsByCompte(int idCompteCourant)
        {
            var depots = await _depotService.GetDepotsByCompteAsync(idCompteCourant);
            return Ok(depots);
        }

        [HttpPost]
        public async Task<ActionResult<Depot>> CreateDepot(Depot depot)
        {
            var createdDepot = await _depotService.CreateDepotAsync(depot);
            return CreatedAtAction(nameof(GetDepotById), new { id = createdDepot.IdDepot }, createdDepot);
        }

        [HttpPut("{id}")]
        public async Task<ActionResult<Depot>> UpdateDepot(int id, Depot depot)
        {
            var updatedDepot = await _depotService.UpdateDepotAsync(id, depot);
            if (updatedDepot == null) return NotFound();
            return Ok(updatedDepot);
        }

        [HttpDelete("{id}")]
        public async Task<ActionResult> DeleteDepot(int id)
        {
            var result = await _depotService.DeleteDepotAsync(id);
            if (!result) return NotFound();
            return NoContent();
        }
    }
}