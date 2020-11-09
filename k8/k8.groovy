node {


	stage ('Loading pipelines scripts '){
		checkout scm 
		common=load "${rootdir}/exp/Common.groovy"
}
}

