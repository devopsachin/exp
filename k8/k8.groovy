node {
	def rootdir="/home/ubunut"

	stage ('Loading pipelines scripts '){
		checkout scm 
		common=load "${rootdir}/exp/Common.groovy"
}
}

