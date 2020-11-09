import java.util.regex.Matcher;		
import java.util.regex.Pattern;		
import groovy.json.JsonSlurper;
import groovy.json.JsonSlurperClassic
import groovy.json.*

import java.util.Map;
import java.io.Reader;
import java.util.HashMap;


def createfile (def filename, def path){
	sh """cd $path """
	sh """ touch $filename """
}


def addrepo(def filename, def path){
	sh """ echo "deb https://artifacts.elastic.co/packages/7.x/apt stable main" | sudo tee $path/$filename"""
}

return this 
