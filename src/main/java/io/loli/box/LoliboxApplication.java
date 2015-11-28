package io.loli.box;

import io.loli.box.service.StorageService;
import io.loli.box.service.impl.AliStorageService;
import io.loli.box.service.impl.FileSystemStorageService;
import io.loli.box.service.impl.QiniuStorageService;
import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableZuulProxy
@EnableJpaRepositories
@Import({MvcConfig.class})
public class LoliboxApplication {
    public static void main(String[] args) {
        SpringApplication.run(LoliboxApplication.class, args);
    }

    @Value("${storage.type}")
    private String storageType;

    @Bean
    public StorageService storageService() {
        switch (storageType) {
            case "aliyun":
                return new AliStorageService();
            case "filesystem":
                return new FileSystemStorageService();
            case "qiniu":
                return new QiniuStorageService();
        }
        return new FileSystemStorageService();
    }

    @Bean
    public Hashids hashids() {
        Hashids hashids = new Hashids(LoliboxApplication.class
                .toString());
        return hashids;
    }
}