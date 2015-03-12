#include <signal.h>
#include "client.h"

bool volatile keepRunning = true;

int main(int argc, char **argv) {

    const char *server_address_string;
    uint16_t server_port;

    if (argc != 3) {
        printf("usage: ./client <IP address> <port>\n");
        exit(EXIT_FAILURE);
    }

    server_address_string = argv[1];
    server_port = (uint16_t) atoi(argv[2]);

    signal(SIGINT, int_handler);

    int socket_descriptor = create_socket();
    struct sockaddr_in server_address = getSocketAddressFor(server_address_string, server_port);
    connect_to(socket_descriptor, &server_address);

    unsigned char nbytes;
    long long number;
    while (keepRunning) {
        ask_for_user_input(&nbytes, &number);
        if (!user_input_valid(nbytes, number)) {
            printf("Invalid values, please try again!\n");
            continue;
        }
        send_no_of_bytes(socket_descriptor, nbytes);
        send_number_as_n_bytes(socket_descriptor, number, nbytes);
        wait_for_an_answer(socket_descriptor);
    }

    close_socket(socket_descriptor);

    return 0;
}

void int_handler(int signum) {
    keepRunning = false;
}

bool user_input_valid(unsigned char nbytes, long long int number) {
    bool nbytes_in_range = nbytes == 1 || nbytes == 2 || nbytes == 4 || nbytes == 8;
    bool number_in_range = number > 0 && number < (2llu << ((nbytes * 8 - 1) - 1));

    return nbytes_in_range && number_in_range;
}

void ask_for_user_input(unsigned char *nbytes, long long int *number) {
    printf("Please specify number of bytes {1, 2, 4, 8} and number: ");
    scanf("%hhu %lld", nbytes, number);
}

void wait_for_an_answer(int descriptor) {
    unsigned char result;
    SAFE_CALL(
            recv(descriptor, &result, sizeof result, 0),
            "Error in recv: "
    );

    printf("Response: %d\n", (int)result);
}

void send_number_as_n_bytes(int descriptor, long long number, unsigned char number_of_bytes) {
    long long network_representation = htonll(number);
    long long shifted_network_representation =
            network_representation >> 8 * (sizeof(long long int) - number_of_bytes);

    SAFE_CALL(
            send(descriptor, &shifted_network_representation, number_of_bytes, 0),
            "Error in send: "
    );
}

void send_no_of_bytes(int descriptor, unsigned char number_of_bytes) {
    SAFE_CALL(
            send(descriptor, &number_of_bytes, sizeof number_of_bytes, 0),
            "Error in send: "
    );
}

void close_socket(int socket_descriptor) {
    SAFE_CALL(
            close(socket_descriptor),
            "Error in close: "
    )
}

int create_socket() {
    int socket_descriptor;
    SAFE_CALL(
            (socket_descriptor = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP)),
            "Error in socket:"
    );
    return socket_descriptor;
}

void connect_to(int socket_descriptor, struct sockaddr_in *server_address) {
    SAFE_CALL(
            connect(socket_descriptor, (struct sockaddr *) server_address, sizeof(*server_address)),
            "Error in connect_to: "
    );

}

struct sockaddr_in getSocketAddressFor(char const *server_address_string, uint16_t server_port) {
    struct sockaddr_in server_address;
    memset(&server_address, 0, sizeof(server_address));

    server_address.sin_family = AF_INET;
    server_address.sin_addr.s_addr = inet_addr(server_address_string);
    server_address.sin_port = htons(server_port);
    return server_address;
}