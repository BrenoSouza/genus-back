package br.edu.ufcg.genus.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.edu.ufcg.genus.models.Notification;

public interface NotificationRepository extends CrudRepository<Notification, Long>{
	
	public Optional<Notification> findById(Long id);

}
