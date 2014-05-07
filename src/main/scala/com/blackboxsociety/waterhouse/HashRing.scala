package com.blackboxsociety.waterhouse

import java.util.zip._
import scala.collection._

case class HashRingNode(value: String, weight: Int)

class HashRing(inputs: HashRingNode*) {

  private val positions = inputs.foldLeft(List[(HashRingNode, List[Long])]()) { (memo, node) =>
    memo :+ (node, generatePositions(node))
  }

  private val positionToNode = positions.foldLeft(Map[Long, String]()) { (memo, tuple) =>
    memo ++ tuple._2.foldLeft(Map[Long, String]()) { (memo, position) =>
      memo + (position -> tuple._1.value)
    }
  }

  private val ring = positions.foldLeft(SortedSet[Long]()) { (memo, tuple) =>
    memo ++ tuple._2
  }

  def get(value: String): Option[String] = {
    for {
      position <- hashToPosition(stringToCRC(value));
      node     <- positionToNode.get(position)
    } yield node
  }

  private def hashToPosition(hash: Long): Option[Long]= {
    ring.from(hash).headOption orElse ring.headOption
  }

  private def stringToCRC(input: String): Long = {
    val crc = new CRC32()
    crc.update(input.getBytes)
    crc.getValue
  }

  private def generatePositions(node: HashRingNode): List[Long] = {
    val range = 1 to node.weight
    range.foldLeft(List[Long]()) { (memo, i) =>
      stringToCRC(node.value + i.toString)  :: memo
    }
  }

}