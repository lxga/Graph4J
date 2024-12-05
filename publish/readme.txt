
In directorul publish
Copie jar-urile si pom-ul intr-un director [ver]
Ruleaza hash.bat in directorul [ver] (punem si public key acolo?)

Scoata balariile din fisierele checksum: "CertUtil: -hashfile command completed successfully."
	sau adauga 2>null la comenzile certutil
	certutil -hashfile <filename> <algorithm> > hash_output.txt 2>nul

Fa o arhiva zip avand org ca radacina org

https://central.sonatype.com/
Sign in with google

Publish Component
https://central.sonatype.com/publishing/deployments
Deployment name: graph4j
Description: A computationally efficient Java library for graph algorithms
Upload your file: zip