package ua.stesh.springsecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.stesh.springsecurity.model.Developer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/developers")
public class DeveloperRestController {

  private List<Developer> DEVELOPERS = Stream.of(
      new Developer(1L, "Taras", "Shevchenko"),
      new Developer(2L, "Lesya", "Ukrainka"),
      new Developer(3L, "Ivan", "Franko")
  ).collect(Collectors.toList());


  @GetMapping
  public List<Developer> getAll() {
    return DEVELOPERS;
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('developers:read')")
  public Developer getById(@PathVariable Long id) {
    return DEVELOPERS.stream()
        .filter(developer -> developer.getId().equals(id))
        .findFirst()
        .orElse(null);
  }

  @PostMapping
  @PreAuthorize("hasAuthority('developers:write')")
  public Developer createDeveloper(@RequestBody Developer developer) {
    this.DEVELOPERS.add(developer);
    return developer;
  }

  @DeleteMapping
  @PreAuthorize("hasAuthority('developers:write')")
  public void deleteDeveloper(@PathVariable Long id) {
    this.DEVELOPERS.removeIf(developer -> developer.getId().equals(id));
  }
}
