node {


	stage ('Loading pipelines scripts '){
		chckout scm 
		common=load "${rootdir}/exp/Common.groovy"
}


