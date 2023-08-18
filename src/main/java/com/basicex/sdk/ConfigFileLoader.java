package com.basicex.sdk;


import com.basicex.sdk.model.YamlConfigModel;
import com.basicex.sdk.util.PrivateKeyUtils;
import com.basicex.sdk.util.X509CertificateUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 * ConfigFileLoader
 */
public class ConfigFileLoader {

    public static BasicExConfig load(String configFilePath) throws IOException, CertificateException {
        YamlConfigModel configModel = loadYaml(configFilePath);

        if(configModel == null) {
            throw new NullPointerException("configModel is null");
        }

        PrivateKey privateKey = PrivateKeyUtils.loadPrivateKey(configModel.getPrivateKey());
        List<X509Certificate> certificateList =  X509CertificateUtils.toX509CertificateList(configModel.getCertificate());
        if(certificateList == null || certificateList.isEmpty()) {
            throw new NullPointerException("certificateList is null or empty");
        }

        return BasicExConfig.builder()
                .privateKey(privateKey)
                .certificate(certificateList.get(0))
                .apiBaseUrl(configModel.getApiBaseUrl())
                .build();
    }

    private static YamlConfigModel loadYaml(String configPath) throws IOException {
        Yaml yaml = new Yaml();
        try(InputStream input = Files.newInputStream(Paths.get(URI.create(configPath)),StandardOpenOption.READ)) {
            return yaml.loadAs(input, YamlConfigModel.class);
        }
    }
}
