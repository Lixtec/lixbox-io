desc_project{
    type="library"
    version{
        majorVersion=11
        mediumVersion=2
        minorVersion=0
    }
    artefact{
        group="fr.lixbox.lixbox-io"
        project="lixbox-io"
        projectKey="${group}:${project}"
    }
}

pic{
    channel="lixbox"
	git{
	    uri="https://github.com/lixbox-team/lixbox-io.git"
	}    	
    jenkins{
        uri="https://ci.service.lixtec.fr/view/${channel}"
    }  
    sonar{
        uri="https://quality.service.lixtec.fr"
    }
    artifactory{
		uri="https://repos.service.lixtec.fr/artifactory"
    }
	mavencentral{
		uri="https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
	}
}

repository{
	artifactory{
	    release	="lixbox-release"
	    snapshot ="lixbox-snapshot"
	    libsRelease="libs-release"
	}
}