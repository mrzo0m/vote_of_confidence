package io.voteofconf.history.service;

import io.voteofconf.history.controller.dto.WebhookSubscription;

public interface CalendlyWebhookService {

    void inviteeCreated(WebhookSubscription invitee);

}
