package uninter.tarefa.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uninter.tarefa.model.Tarefa;
import uninter.tarefa.repository.TarefaRepository;

@RestController
@RequestMapping("/tarefa")
public class TarefaController {

    private final TarefaRepository repository;

    public TarefaController(TarefaRepository tarefaRepository) {
        this.repository = tarefaRepository;
    }

    @GetMapping
    public List<Tarefa> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable long id) {
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Tarefa create(@RequestBody Tarefa tarefa) {
        return repository.save(tarefa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody Tarefa tarefa) {
        return repository.findById(id).map(record -> {
            record.setNomeTarefa(tarefa.getNomeTarefa());
            record.setDataEntrega(tarefa.getDataEntrega());
            record.setResponsavelTarefa(tarefa.getResponsavelTarefa());
            Tarefa updated = repository.save(record);
            return ResponseEntity.ok().body(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        return repository.findById(id).map(record -> {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
