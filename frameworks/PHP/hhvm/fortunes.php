<?php
//
// 4. Fortunes
//

require_once dirname(__FILE__).'/once.php.inc';

function main() {
    $b = new Benchmark();
    $b->bench_fortunes();
}

main();
