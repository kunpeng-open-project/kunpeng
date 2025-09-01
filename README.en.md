# Kunpeng Microservice Framework

A lightweight microservice framework built on technical stacks such as SpringBoot, SpringCloud, and SpringCloud Alibaba, dedicated to providing developers with a full-stack solution for quick adoption.

## Project Introduction

The Kunpeng Microservice Framework is an open-source project designed to meet the efficient development needs of development teams. It integrates current mainstream microservice technologies, providing a one-stop solution from code generation to service deployment, helping developers quickly build stable and efficient microservice applications.

## Online Experience

- Official Website (Documentation): [http://kpopen.cn](http://kpopen.cn)
- Demo Address: [http://kpopen.cn/auth](http://kpopen.cn/auth)
- Interface Documentation: [http://kpopen.cn/gateway/doc.html](http://kpopen.cn/gateway/doc.html)

## Open-Source Motivation

When searching for microservice frameworks suitable for developers at all levels, it was found that existing frameworks struggled to meet the following requirements:
- Automatic generation of Controller, Service, DAO, and PO classes
- Automatic generation of basic functional interfaces like CRUD based on database tables
- Unified interface style and address specifications
- Framework-level transaction management to avoid manual annotation omissions
- Encapsulation of common functions and middleware utility classes to lower the technical threshold
- Complete interface call records, error logs, and call statistics
- Open-source and modifiable core code for easy secondary development

Therefore, the Kunpeng Microservice Framework was developed in spare time to meet these needs.

## Core Features

- **Automatic Code Generation**: Generate basic code based on database tables to reduce repetitive development
- **Full-Stack Microservice Solution**: Integrates the SpringCloud ecosystem, supporting a complete microservice architecture with service registration, configuration center, etc.
- **Zero Learning Curve**: Encapsulates common functions and provides clear usage examples
- **Rich Utility Classes**: Integrates various commonly used tools in development to improve efficiency
- **Annotation-Driven Development**: Simplifies the development process through annotations, reducing coding complexity
- **Knife4j Extension**: Perfect interface documentation support for convenient team collaboration
- **High Configurability**: Flexible configuration solutions to adapt to different business scenarios

### Core Frameworks
- Spring Boot: Foundation framework for rapid application construction
- Spring Cloud: Core components of the microservice architecture
- Spring Cloud Alibaba: Integration of Alibaba's microservice components
- MyBatis-Plus: Enhanced MyBatis for simplified database operations
- MyBatis-Plus-Join: Supports complex association queries

## Supported Databases

- MySQL (default)
- Oracle
- SQL Server
- PostgreSQL

### Environment Requirements

- JDK 1.8+
- Maven 3.6+
- MySQL >= 8.0 (or other supported databases)