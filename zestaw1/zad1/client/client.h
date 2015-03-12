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
#include <stdbool.h>

#define SAFE_CALL(CALL, message) \
if ((CALL) == -1) { \
    perror((message)); \
    exit(EXIT_FAILURE); \
}

#define htonll(x) ((((uint64_t)htonl(x)) << 32) + htonl((x) >> 32))

struct sockaddr_in getSocketAddressFor(char const *server_address_string, uint16_t server_port);

void connect_to(int socket_descriptor, struct sockaddr_in *server_address);

int create_socket();

void close_socket(int socket_descriptor);

void send_no_of_bytes(int descriptor, unsigned char number_of_bytes);

void send_number_as_n_bytes(int descriptor, long long number, unsigned char number_of_bytes);

void wait_for_an_answer(int descriptor);

void ask_for_user_input(unsigned char *nbytes, long long int *number);

bool user_input_valid(unsigned char nbytes, long long int number);

void int_handler(int signum);
