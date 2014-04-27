package com.blackboxsociety.waterhouse

object HashList {

  case class Container[A <: Hash.Algorithm](top: Hash.Digest[A], list: Seq[Hash.Digest[A]])(implicit algorithm: A)

  case class Block(content: Array[Byte], offset: Int)

  def verifyTopHash[A <: Hash.Algorithm](top: Hash.Digest[A],
                                         list: Seq[Hash.Digest[A]])(implicit algorithm: A): Boolean =
  {
    top.digest == Hash.bytesDigest[A](list.map(_.digest).reduce({(m, n) => m ++ n}))
  }

  def verifyBlock[A <: Hash.Algorithm](block: Block, list: Seq[Hash.Digest[A]])(implicit algorithm: A): Boolean = {
    list.lift(block.offset)
        .filter({ hash => hash.digest == Hash.bytesDigest[A](block.content) })
        .nonEmpty
  }

  def generate[A <: Hash.Algorithm](content: Array[Byte], blockSize: Int)(implicit algorithm: A): Container[A] = {
    val list = generateList[A](content, blockSize)
    Container(generateTopHash(list), list)
  }

  def generateList[A <: Hash.Algorithm](content: Array[Byte],
                                        blockSize: Int)(implicit algorithm: A): Seq[Hash.Digest[A]] =
  {
    content.grouped(blockSize).map({ chunk => Hash.digest[A](chunk) }).toList
  }

  def generateTopHash[A <: Hash.Algorithm](list: Seq[Hash.Digest[A]])(implicit algorithm: A): Hash.Digest[A] = {
    Hash.digest[A](list.map(_.digest).reduce({(m, n) => m ++ n}))
  }

}