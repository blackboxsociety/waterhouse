package com.blackboxsociety.waterhouse

object HashChain {

  def chain[A <: Hash.Algorithm](bytes: Array[Byte], iterations: Int)(implicit algorithm: A): Hash.Result[A] = {
    val chained = (1 to iterations).foldLeft[Array[Byte]](bytes) { (m, n) =>
      Hash.bytesDigest(m)
    }
    Hash.Result[A](chained)
  }

  def chain[A <: Hash.Algorithm](text: String, iterations: Int)(implicit algorithm: A): Hash.Result[A] = {
    chain[A](text.getBytes, iterations)
  }

}
