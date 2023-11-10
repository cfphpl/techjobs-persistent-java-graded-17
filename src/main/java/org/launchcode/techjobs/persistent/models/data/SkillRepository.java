package org.launchcode.techjobs.persistent.models.data;

//Mom's surgery 16th Anderson Hospital. Arrive at noon. Surgery at 2pm
// KEVIN delete this once you put it in the calendar!
import org.launchcode.techjobs.persistent.models.Skill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends CrudRepository<Skill,Integer> {

}