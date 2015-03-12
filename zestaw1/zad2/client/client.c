#include "client.h"

int main(int argc, char **argv) {

    const char *server_address_string, *filename;
    uint16_t server_port;

    if (argc != 4) {
        printf("usage: ./client <IP address> <port> <filename>\n");
        exit(EXIT_FAILURE);
    }

    server_address_string = argv[1];
    server_port = (uint16_t) atoi(argv[2]);
    filename = argv[3];

    int socket_descriptor = create_socket();
    struct sockaddr_in server_address = getSocketAddressFor(server_address_string, server_port);
    connect_to(socket_descriptor, &server_address);
    int file_descriptor = openFile(filename);

    send_file_name(filename, socket_descriptor);
    send_content(file_descriptor, socket_descriptor);

    close_descriptor(file_descriptor);
    close_descriptor(socket_descriptor);

    return 0;
}

void close_descriptor(int descriptor) {
    SAFE_CALL(
            close(descriptor),
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

int openFile(char const *filename) {
    int file_descriptor;

    SAFE_CALL(
            (file_descriptor = open(filename, O_RDONLY)),
            "Error in open: "
    )

    return file_descriptor;
}

void send_file_name(char const *filename, int socket_descriptor) {
    unsigned char filename_len = (unsigned char) strlen(filename);
    SAFE_CALL(
            send(socket_descriptor, &filename_len, sizeof filename_len, 0),
            "Error in send: "
    );

    SAFE_CALL(
            send(socket_descriptor, filename, strlen(filename), 0),
            "Error in send: "
    );

}

void send_content(int file_descriptor, int socket_descriptor) {
    char buffer[BUFFER_SIZE];
    ssize_t bytes_read;

    SAFE_CALL(
            (bytes_read = read(file_descriptor, &buffer, sizeof buffer)),
            "Error in read: "
    );

    while (bytes_read != 0) {
        SAFE_CALL(
                send(socket_descriptor, &buffer, bytes_read, 0),
                "Error in send: "
        );

        SAFE_CALL(
                (bytes_read = read(file_descriptor, &buffer, sizeof buffer)),
                "Error in read: "
        );
    }
}
