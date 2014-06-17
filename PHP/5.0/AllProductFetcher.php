<?php

// Include the Vindicia library
require_once("../../../API/50/Vindicia/Soap/Vindicia.php");
require_once("../../../API/50/Vindicia/Soap/Const.php");

$product = new Product();
$page = 0;
$pageSize = 10;
$hasMore = true;
$totalProductCount = 0;
do {
    $ret = $product->fetchAll($page, $pageSize);
    $count = 0;
    if ($ret['returnCode'] == 200) {
        $fetchedProducts = $ret['data']->products;
        if ($fetchedProducts != null) {
            $count = sizeof($fetchedProducts);
            print($count.' products returned on page '.$page.PHP_EOL);
            foreach ($fetchedProducts as $prod) {
                print('Product Id='.$prod->merchantProductId.PHP_EOL);
                print('Product Description='.$prod->descriptions[0]->description.PHP_EOL);
                $totalProductCount++;
            }
            $page++;
        }
        else {
            print('No products returned on page '.$page.PHP_EOL);
            $hasMore = false;
        }
    }
    else if ($ret['returnCode'] == 404) {
        print('No products returned on page '. $page.PHP_EOL);
        $hasMore = false;
    }
    else {
        print('Product fetch failed on page '. $page.PHP_EOL);
        $hasMore = false;
    }
} while ($hasMore);

print('Fetched total='.$totalProductCount.PHP_EOL);
