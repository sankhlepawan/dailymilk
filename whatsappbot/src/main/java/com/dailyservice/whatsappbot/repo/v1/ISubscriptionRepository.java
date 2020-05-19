package com.dailyservice.whatsappbot.repo.v1;

import org.springframework.data.repository.CrudRepository;

import com.dailyservice.whatsappbot.model.v1.Subscription;

public interface ISubscriptionRepository extends CrudRepository<Subscription, Integer>{

}
