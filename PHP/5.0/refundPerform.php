// Create a refund object

$tx_id = $argv[1];
$amount = $argv[2];

$refund1 = new Refund();

$transaction = new Transaction();
// merchant ID of a successful transaction that we wish to refund
$transaction->setMerchantTransactionId($tx_id);

//Alternatively, you can use the VID of the transaction
$transaction->setVID($tx_vid);


$refund1->setTransaction($transaction);
$refund1->setAmount($amount);


$refund1->setNote('Refunding test purchase');

$refund = new Refund();

print "Performing refund for $amount on transaction $tx_id \n";
$response = $refund->perform(array($refund1));
print_r ($response);

?>
