package br.com.record.base;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Value("${record.rabbitmq.exchange}")
    private String exchange;

    @Value("${record.rabbitmq.routingKey}")
    private String routingKey;

    private RabbitTemplate rabbitTemplate;

    private PersonRepository personRepository;

    @Autowired
    public void setPersonRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping
    public ResponseEntity get() {

        Person person = new Person();
        person.setName("Taliba Jose");
        person.setPassword("123456");
        person.setUsername("teste");
        person.setEmail("thiago@thiago.com.br");

        Person p = personRepository.save(person);

        rabbitTemplate.convertAndSend(exchange, routingKey, p);
        return ResponseEntity.ok(p);
    }

    @PostMapping
    public ResponseEntity save(Person person) {

        return ResponseEntity.ok(person);

    }

}
