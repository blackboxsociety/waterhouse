package com.blackboxsociety.waterhouse

import java.security.SecureRandom
import scalaz.concurrent.Task

class Random {

  val seed = new SecureRandom()

  def next(size: Int): Task[(Array[Byte], Random)] = {
    Task.now {
      val bytes = new Array[Byte](size)
      seed.nextBytes(bytes)
      (bytes, this)
    }
  }

}
