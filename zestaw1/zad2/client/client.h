#include <sys/socket.h>
#include <netinet/in.h>
#include <stdio.h>
#include <string.h>
#include <arpa/inet.h>
#include <stdlib.h>
#include <stdint.h>
#include <fcntl.h>
#include <unistd.h>
#include <stdint-gcc.h>
#include <netinet/in.h>
#include <string.h>
#include <bits/socket.h>
#include <arpa/inet.h>

#define BUFFER_SIZE 256

#define SAFE_CALL(CALL, message) \
if ((CALL) == -1) { \
    perror((message)); \
    exit(EXIT_FAILURE); \
}

void send_file_name(char const *filename, int socket_descriptor);

void send_content(int file_descriptor, int socket_descriptor);

int openFile(char const *filename);

struct sockaddr_in getSocketAddressFor(char const *server_address_string, uint16_t server_port);

void connect_to(int socket_descriptor, struct sockaddr_in *server_address);

int create_socket();

void close_descriptor(int descriptor);
