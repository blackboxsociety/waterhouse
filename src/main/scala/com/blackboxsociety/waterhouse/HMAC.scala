package com.blackboxsociety.waterhouse

import javax.crypto.spec.SecretKeySpec
import javax.crypto.Mac

object HMAC {

  sealed trait Algorithm {
    val id: String
  }
  implicit object Md5 extends Algorithm {
    val id = "HmacMD5"
  }
  implicit object Sha1 extends Algorithm {
    val id = "HmacSHA1"
  }
  implicit object Sha256 extends Algorithm {
    val id = "HmacSHA256"
  }
  implicit object Sha384 extends Algorithm {
    val id = "HmacSHA384"
  }
  implicit object Sha512 extends Algorithm {
    val id = "HmacSHA512"
  }

  case class Secret(key: String)

  case class Digest[A <: Algorithm](hash: Array[Byte])(implicit algorithm: A)

  def digest[A <: Algorithm](secret: Secret, text: String)(implicit algorithm: A): Digest[A] = {
    digest[A](secret, text.getBytes)
  }

  def digest[A <: Algorithm](secret: Secret, bytes: Array[Byte])(implicit algorithm: A): Digest[A] = {
    Digest[A](bytesDigest[A](secret, bytes))
  }

  def stringDigest[A <: Algorithm](secret: Secret, text: String)(implicit algorithm: A): String = {
    bytesDigest[A](secret, text.getBytes).map("%02X" format _).mkString
  }

  def bytesDigest[A <: Algorithm](secret: Secret, bytes: Array[Byte])(implicit algorithm: A): Array[Byte] = {
    val key = new SecretKeySpec(secret.key.getBytes, algorithm.id)
    val mac = Mac.getInstance(algorithm.id)
    mac.init(key)
    mac.doFinal(bytes)
  }

  def verify[A <: Algorithm](signature: Digest[A],
                             content: Array[Byte],
                             secret: Secret)(implicit algorithm: A): Boolean =
  {
    signature.hash == bytesDigest[A](secret, content)
  }

}