package com.blackboxsociety.waterhouse

import java.security.MessageDigest

object Hash {

  sealed trait Algorithm {
    val id: String
  }
  implicit object Md2 extends Algorithm {
    val id = "MD2"
  }
  implicit object Md5 extends Algorithm {
    val id = "MD5"
  }
  implicit object Sha1 extends Algorithm {
    val id = "SHA-1"
  }
  implicit object Sha256 extends Algorithm {
    val id = "SHA-256"
  }
  implicit object Sha384 extends Algorithm {
    val id = "SHA-384"
  }
  implicit object Sha512 extends Algorithm {
    val id = "SHA-512"
  }

  case class Digest[A <: Algorithm](digest: Array[Byte])(implicit algorithm: A)

  def digest[A <: Algorithm](text: String)(implicit algorithm: A): Digest[A] = {
    digest[A](text.getBytes)
  }

  def digest[A <: Algorithm](bytes: Array[Byte])(implicit algorithm: A): Digest[A] = {
    Digest[A](bytesDigest[A](bytes))
  }

  def stringDigest[A <: Algorithm](text: String)(implicit algorithm: A): String = {
    bytesDigest[A](text.getBytes).map("%02X" format _).mkString
  }

  def bytesDigest[A <: Algorithm](bytes: Array[Byte])(implicit algorithm: A): Array[Byte] = {
    MessageDigest.getInstance(algorithm.id).digest(bytes)
  }

}
