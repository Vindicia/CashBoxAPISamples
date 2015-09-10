<?php

// This example assumes the following function is within a Symfony app with PDO database connection configured.
// The function registers a route https://some_url/push to which the CashBox servers are configured to post
// json formatted entitlement notifications.
$app->post('/push', function(Request $request) use($app) {
    $payload = $request->getContent();
    $hmac_key = 'a-40-character-key-provided-from-CashBox-merchant-config';
    parse_str($payload, $parsed_variables);
    $json = $parsed_variables['json'];

    $variables = json_decode($json, true);
    $content = $variables['content'];
    $app['monolog']->addDebug($content['merchantAutobillId']);
    //Assume only one entitlement in array.  Depending on configuration, this array could have multiple items.
    $app['monolog']->addDebug($content['merchantEntitlementId'][0]);
    $app['monolog']->addDebug($content['merchantAccountId']);
    $app['monolog']->addDebug($content['entitlementSource']);
    $app['monolog']->addDebug($content['startTimestamp']);
    $app['monolog']->addDebug($content['endTimestamp']);
    $header = $variables['header'];
    $app['monolog']->addDebug($header['author_id']);
    $app['monolog']->addDebug($header['class_name']);
    $app['monolog']->addDebug($header['message_id']);
    $app['monolog']->addDebug($header['event_name']);
    $app['monolog']->addDebug($header['merchant_id']);
    $app['monolog']->addDebug($header['submitted']);
    $app['monolog']->addDebug($header['event_timestamp']);

    $signature = $parsed_variables['signature'];
    $hash = hash_hmac('sha256', $json, $hmac_key, true);
    $base64hash = base64_encode($hash);
    if ($base64hash != $signature) {
        $app['monolog']->addDebug('No match: ' . $base64hash . ' is not equal to ' . $signature);
        // Return 403 forbidden.
        return $app->json('Sorry, signature did not match for :' . $header['message_id'], 403);
    } else {
        $app['monolog']->addDebug('We have a match: ' . $base64hash . ' is equal to ' . $signature);
    }

    $now = new \DateTime('now', new \DateTimeZone('America/Los_Angeles'));
    $received_timestamp = $now->format(DateTime::ATOM);
    $st = $app['pdo']->prepare('INSERT INTO entitlement_push(received_timestamp, merchant_autobill_id, ' .
        'merchant_entitlement_id, merchant_account_id, entitlement_source, start_timestamp, end_timestamp, ' .
        'author_id, class_name, message_id, event_name, merchant_id, submitted, event_timestamp, ' .
        'basehash, signature) ' .
        'VALUES(:received_timestamp, :merchant_autobill_id, ' .
        ':merchant_entitlement_id, :merchant_account_id, :entitlement_source, :start_timestamp, :end_timestamp, ' .
        ':author_id, :class_name, :message_id, :event_name, :merchant_id, :submitted, :event_timestamp, ' .
        ':basehash, :signature)');
    $st->execute(array(':received_timestamp' => $received_timestamp,
        ':merchant_autobill_id' => $content['merchantAutobillId'],
        ':merchant_entitlement_id' => $content['merchantEntitlementId'][0],
        ':merchant_account_id' => $content['merchantAccountId'],
        ':entitlement_source' => $content['entitlementSource'],
        ':start_timestamp' => $content['startTimestamp'],
        ':end_timestamp' => $content['endTimestamp'],
        ':author_id' => $header['author_id'],
        ':class_name' => $header['class_name'],
        ':message_id' => $header['message_id'],
        ':event_name' => $header['event_name'],
        ':merchant_id' => $header['merchant_id'],
        ':submitted' => $header['submitted'],
        ':event_timestamp' => $header['event_timestamp'],
        ':basehash' => $base64hash,
        ':signature' => $signature
    ));

    return $app->json('Thank you for message_id:' . $header['message_id'], 202);
});
