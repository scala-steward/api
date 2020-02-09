variable "env" {
  description = "Name of the environment"
}

variable "api_role" {
  description = "IAM role for the api container"
}

variable "cpu" {
  description = "Default size of instances created"
}

variable "memory" {
  description = "Default size of instances created"
}

variable "vpc_id" {
  description = "The id of the VPC this app will be installed in"
}

variable "private_subnet_ids" {
  description = "The private subnet ids to install this service on"
}

variable "public_subnet_ids" {
  description = "The public subnet ids to install the load balancer on"
}

variable "database_endpoint" {
  description = "Where to connect to the database"
}

variable "rds_username" {
  default = "test_username"
}

variable "rds_password" {
  default = "test_username"
}

variable "secret_key_base" {
  description = "The key to use for encryption within the service"
}
