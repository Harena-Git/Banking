using Microsoft.AspNetCore.Mvc;
using DepositAccount.Models;
using DepositAccount.Services;

namespace DepositAccount.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class InteretController : ControllerBase
    {
        private readonly IInteretService _interetService;

        public InteretController(IInteretService interetService)
        {
            _interetService = interetService;
        }

        [HttpGet]
        public async Task<ActionResult<List<Interet>>> GetAllInterets()
        {
            var interets = await _interetService.GetAllInteretsAsync();
            return Ok(interets);
        }

        [HttpGet("{id}")]
        public async Task<ActionResult<Interet>> GetInteretById(int id)
        {
            var interet = await _interetService.GetInteretByIdAsync(id);
            if (interet == null) return NotFound();
            return Ok(interet);
        }

        [HttpGet("depot/{idDepot}")]
        public async Task<ActionResult<List<Interet>>> GetInteretsByDepot(int idDepot)
        {
            var interets = await _interetService.GetInteretsByDepotAsync(idDepot);
            return Ok(interets);
        }

        [HttpPost]
        public async Task<ActionResult<Interet>> CreateInteret(Interet interet)
        {
            var createdInteret = await _interetService.CreateInteretAsync(interet);
            return CreatedAtAction(nameof(GetInteretById), new { id = createdInteret.IdInteret }, createdInteret);
        }

        [HttpPut("{id}")]
        public async Task<ActionResult<Interet>> UpdateInteret(int id, Interet interet)
        {
            var updatedInteret = await _interetService.UpdateInteretAsync(id, interet);
            if (updatedInteret == null) return NotFound();
            return Ok(updatedInteret);
        }

        [HttpDelete("{id}")]
        public async Task<ActionResult> DeleteInteret(int id)
        {
            var result = await _interetService.DeleteInteretAsync(id);
            if (!result) return NotFound();
            return NoContent();
        }

        [HttpPost("calculate")]
        public async Task<ActionResult<decimal>> CalculateInteret([FromBody] CalculationRequest request)
        {
            var interet = await _interetService.CalculateInteretAsync(request.Montant, request.Pourcentage, request.Duree);
            return Ok(interet);
        }
    }

    public class CalculationRequest
    {
        public decimal Montant { get; set; }
        public decimal Pourcentage { get; set; }
        public int Duree { get; set; }
    }
}