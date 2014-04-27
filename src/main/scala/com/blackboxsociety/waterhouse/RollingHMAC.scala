package com.blackboxsociety.waterhouse

object RollingHMAC {

  def verify[A <: HMAC.Algorithm](digest: HMAC.Digest[A],
                                  content: Array[Byte],
                                  secrets: Seq[HMAC.Secret])(implicit algorithm: A): Boolean =
  {
    secrets.foldLeft[Boolean](false) { (m, n) => m || HMAC.verify[A](digest, content, n) }
  }

  def verify[A <: HMAC.Algorithm](digest: HMAC.Digest[A],
                                  content: String,
                                  secrets: Seq[HMAC.Secret])(implicit algorithm: A): Boolean =
  {
    verify[A](digest, content.getBytes, secrets)
  }

}
