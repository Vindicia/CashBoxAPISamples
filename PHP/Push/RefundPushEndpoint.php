<?php

// This example assumes the following function is within a Symfony app with PDO database connection configured.
// The function registers a route https://some_url/push to which the CashBox servers are configured to post
// json formatted refund notifications.
$app->post('/push', function(Request $request) use($app) {
    $payload = $request->getContent();
    $hmac_key = 'a-40-character-key-provided-from-CashBox-merchant-config';
    parse_str($payload, $parsed_variables);
    $json = $parsed_variables['json'];

    $variables = json_decode($json, true);
    $content = $variables['content'];
    $app['monolog']->addDebug($content['merchantRefundId']);
    $app['monolog']->addDebug($content['note']);
    $app['monolog']->addDebug($content['currency']);
    $app['monolog']->addDebug($content['amount']);
    $app['monolog']->addDebug($content['timestamp']);
    $app['monolog']->addDebug($content['transaction']);
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

    return $app->json('Thank you for message_id:' . $header['message_id'], 202);
});
