package org.apatheia.model

import org.apatheia.error.PackageDataParsingError

trait PackageDataParser[T] {
  def parse(byteArray: Array[Byte]): Either[PackageDataParsingError, T]
}
