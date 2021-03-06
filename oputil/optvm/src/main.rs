#![feature(duration_as_u128)]
#![allow(dead_code)]
#![allow(unused_imports)]
#![allow(non_snake_case)]
#![allow(unused_must_use)]
#![allow(unused_variables)]
#![allow(unreachable_code)]

use consts::*;
use std::time::Instant;

mod executor;
mod decoder;
mod memory;
mod consts;
mod builder;


fn main() {
    let mut cpu = builder::build();

    // run simulator
    let start = Instant::now();
    executor::execute(&mut cpu);
    println!("timer: {} ms", start.elapsed().as_millis());
}
