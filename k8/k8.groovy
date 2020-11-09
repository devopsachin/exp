node {
	def rootdir="/home/ubuntu"

	stage ('Loading pipelines scripts '){
		checkout scm 
		common=load "${rootdir}/exp/Common.groovy"
}

	stage ('update the syste'){
		common.upgrade()
}
}

