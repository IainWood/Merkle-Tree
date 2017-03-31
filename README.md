# Merkle-Tree
Implementation of a Merkle Tree

usage: java merkle.Process [options]
-i is a required option
	-i <path to input file>
	-hf <hash function> [default md5]
		md5 for MD5
		sum for SumHash
	-b <block size> [default 1024]
	-c <verify count> [default 10]
	-v
		for verbose output
	-h or --help
		help

---------------------------------------------------------------------------------------------------------------------------------------------------
	ADVANCED OPTIONS - BEWARE
	-o <path to output file> [default inputFile.spoof]
	-t <task> [default generate]
		generate to generate a merkle tree
		spoof to create a spoof file whose merkle tree matches the input file
	-d
	    to dump the merkle tree to the specified output file
