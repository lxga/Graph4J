certutil -hashfile graph4j-1.0.8.jar MD5 > graph4j-1.0.8.jar.md5
certutil -hashfile graph4j-1.0.8.jar SHA1 > graph4j-1.0.8.jar.sha1
gpg --detach-sign -a graph4j-1.0.8.jar

certutil -hashfile graph4j-1.0.8.pom MD5 > graph4j-1.0.8.pom.md5
certutil -hashfile graph4j-1.0.8.pom SHA1 > graph4j-1.0.8.pom.sha1
gpg --detach-sign -a graph4j-1.0.8.pom

certutil -hashfile graph4j-1.0.8-javadoc.jar MD5 > graph4j-1.0.8-javadoc.jar.md5
certutil -hashfile graph4j-1.0.8-javadoc.jar SHA1 > graph4j-1.0.8-javadoc.jar.sha1
gpg --detach-sign -a graph4j-1.0.8-javadoc.jar

certutil -hashfile graph4j-1.0.8-sources.jar MD5 > graph4j-1.0.8-sources.jar.md5
certutil -hashfile graph4j-1.0.8-sources.jar SHA1 > graph4j-1.0.8-sources.jar.sha1
gpg --detach-sign -a graph4j-1.0.8-sources.jar
